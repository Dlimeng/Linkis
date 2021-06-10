package com.webank.wedatasphere.linkis.manager.am.restful;


import com.webank.wedatasphere.linkis.common.exception.DWCRetryException;
import com.webank.wedatasphere.linkis.manager.am.service.engine.*;
import com.webank.wedatasphere.linkis.manager.common.entity.node.AMEMNode;
import com.webank.wedatasphere.linkis.manager.common.entity.node.EngineNode;
import com.webank.wedatasphere.linkis.manager.common.protocol.engine.*;
import com.webank.wedatasphere.linkis.message.builder.MessageJob;
import com.webank.wedatasphere.linkis.message.publisher.MessagePublisher;
import com.webank.wedatasphere.linkis.common.ServiceInstance;
import com.webank.wedatasphere.linkis.manager.am.conf.AMConfiguration;
import com.webank.wedatasphere.linkis.manager.am.exception.AMErrorCode;
import com.webank.wedatasphere.linkis.manager.am.exception.AMErrorException;
import com.webank.wedatasphere.linkis.manager.am.service.engine.EngineInfoService;
import com.webank.wedatasphere.linkis.manager.am.utils.AMUtils;
import com.webank.wedatasphere.linkis.manager.am.vo.AMEngineNodeVo;
import com.webank.wedatasphere.linkis.manager.am.vo.EMNodeVo;
import com.webank.wedatasphere.linkis.manager.common.entity.enumeration.NodeHealthy;
import com.webank.wedatasphere.linkis.manager.common.entity.enumeration.NodeStatus;
import com.webank.wedatasphere.linkis.manager.common.entity.metrics.NodeHealthyInfo;
import com.webank.wedatasphere.linkis.manager.common.entity.node.AMEMNode;
import com.webank.wedatasphere.linkis.manager.common.entity.node.EngineNode;
import com.webank.wedatasphere.linkis.manager.label.builder.factory.LabelBuilderFactory;
import com.webank.wedatasphere.linkis.manager.label.builder.factory.LabelBuilderFactoryContext;
import com.webank.wedatasphere.linkis.manager.label.builder.factory.StdLabelBuilderFactory;
import com.webank.wedatasphere.linkis.manager.label.entity.Label;
import com.webank.wedatasphere.linkis.manager.label.entity.UserModifiable;
import com.webank.wedatasphere.linkis.manager.label.exception.LabelErrorException;
import com.webank.wedatasphere.linkis.manager.label.service.NodeLabelService;
import com.webank.wedatasphere.linkis.server.Message;
import com.webank.wedatasphere.linkis.server.security.SecurityFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;



@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
@Path("/linkisManager")
public class EngineRestfulApi {

    @Autowired
    private EngineAskEngineService engineAskEngineService;
    @Autowired
    private EngineInfoService engineInfoService;
    @Autowired
    private EngineCreateService engineCreateService;
    @Autowired
    private EngineReuseService engineReuseService;
    @Autowired
    private EngineStopService engineStopService;
    @Autowired
    private MessagePublisher messagePublisher;

    @Autowired
    private NodeLabelService nodeLabelService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private LabelBuilderFactory stdLabelBuilderFactory = LabelBuilderFactoryContext.getLabelBuilderFactory();

    private Logger logger = LoggerFactory.getLogger(EMRestfulApi.class);

    @GET
    @Path("/listUserEngines")
    public Response listUserEngines(@Context HttpServletRequest req) {
        String userName = SecurityFilter.getLoginUsername(req);
        List<EngineNode> engineNodes = engineInfoService.listUserEngines(userName);
        return Message.messageToResponse(Message.ok().data("engines", engineNodes));
    }

    @POST
    @Path("/listEMEngines")
    public Response listEMEngines(@Context HttpServletRequest req, JsonNode jsonNode) throws IOException, AMErrorException {
        String username = SecurityFilter.getLoginUsername(req);
        String[] adminArray = AMConfiguration.GOVERNANCE_STATION_ADMIN().getValue().split(",");
        if(adminArray != null && !Arrays.asList(adminArray).contains(username)){
            throw new AMErrorException(210003,"only admin can search engine information(只有管理员才能查询所有引擎信息)");
        }
        AMEMNode amemNode = objectMapper.readValue(jsonNode.get("em"), AMEMNode.class);
        JsonNode emInstace = jsonNode.get("emInstance");
        JsonNode nodeStatus = jsonNode.get("nodeStatus");
        JsonNode owner = jsonNode.get("owner");
        List<EngineNode> engineNodes = engineInfoService.listEMEngines(amemNode);
        ArrayList<AMEngineNodeVo> allengineNodes = AMUtils.copyToAMEngineNodeVo(engineNodes);
        ArrayList<AMEngineNodeVo> allEMVoFilter1 = allengineNodes;
        if(CollectionUtils.isNotEmpty(allEMVoFilter1) && emInstace != null){
            allEMVoFilter1 = (ArrayList<AMEngineNodeVo>) allEMVoFilter1.stream().filter(em -> {return em.getInstance().contains(emInstace.asText());}).collect(Collectors.toList());
        }
        ArrayList<AMEngineNodeVo> allEMVoFilter2 = allEMVoFilter1;
        if(CollectionUtils.isNotEmpty(allEMVoFilter2) && nodeStatus != null && !StringUtils.isEmpty(nodeStatus.asText())){
            allEMVoFilter2 = (ArrayList<AMEngineNodeVo>) allEMVoFilter2.stream().filter(em -> {return em.getNodeStatus() != null ? em.getNodeStatus().equals(NodeStatus.valueOf(nodeStatus.asText())) : true;}).collect(Collectors.toList());
        }
        ArrayList<AMEngineNodeVo> allEMVoFilter3 = allEMVoFilter2;
        if(CollectionUtils.isNotEmpty(allEMVoFilter3) && owner != null && !StringUtils.isEmpty(owner.asText())){
            allEMVoFilter3 = (ArrayList<AMEngineNodeVo>) allEMVoFilter3.stream().filter(em ->{return em.getOwner().equalsIgnoreCase(owner.asText());}).collect(Collectors.toList());
        }
        return Message.messageToResponse(Message.ok().data("engines", allEMVoFilter3));
    }

    @PUT
    @Path("/modifyEngineInfo")
    public Response modifyEngineInfo(@Context HttpServletRequest req, JsonNode jsonNode) throws AMErrorException, LabelErrorException {
        String username = SecurityFilter.getLoginUsername(req);
        String[] adminArray = AMConfiguration.GOVERNANCE_STATION_ADMIN().getValue().split(",");
        if(adminArray != null && !Arrays.asList(adminArray).contains(username)){
            throw new AMErrorException(210003,"only admin can modify engine information(只有管理员才能修改引擎信息)");
        }
        String applicationName = jsonNode.get("applicationName").asText();
        String instance = jsonNode.get("instance").asText();
        if(StringUtils.isEmpty(applicationName)){
            throw new AMErrorException(AMErrorCode.QUERY_PARAM_NULL.getCode(), "applicationName cannot be null(请求参数applicationName不能为空)");
        }
        if(StringUtils.isEmpty(instance)){
            throw new AMErrorException(AMErrorCode.QUERY_PARAM_NULL.getCode(), "instance cannot be null(请求参数instance不能为空)");
        }
        ServiceInstance serviceInstance = ServiceInstance.apply(applicationName,instance);
        if(serviceInstance == null){
            throw new AMErrorException(AMErrorCode.QUERY_PARAM_NULL.getCode(),"serviceInstance:" + applicationName + " non-existent(服务实例" + applicationName + "不存在)");
        }
        JsonNode labels = jsonNode.get("labels");
        Set<String> labelKeySet = new HashSet<>();
        if(labels != null){
            ArrayList<Label<?>> newLabelList = new ArrayList<>();
            Iterator<JsonNode> iterator = labels.iterator();
            while(iterator.hasNext()){
                JsonNode label = iterator.next();
                String labelKey = label.get("labelKey").asText();
                String stringValue = label.get("stringValue").asText();
                Label newLabel = stdLabelBuilderFactory.createLabel(labelKey, stringValue);
                if(newLabel instanceof UserModifiable) {
                    ((UserModifiable) newLabel).valueCheck(stringValue);
                }
                labelKeySet.add(labelKey);
                newLabelList.add(newLabel);
            }
            if(labelKeySet.size() != newLabelList.size()){
               throw new AMErrorException(210003, "Failed to update label, include repeat label(更新label失败，包含重复label)");
            }
            nodeLabelService.updateLabelsToNode(serviceInstance, newLabelList);
            logger.info("success to update label of instance: " + serviceInstance.getInstance());
        }
        return Message.messageToResponse(Message.ok("success to update engine information(更新引擎信息成功)"));
    }
    @POST
    @Path("/askEngine")
    public Response askEngine(@Context HttpServletRequest req, Map<String,Object> json) throws IOException {
        EngineAskRequest engineAskRequest = objectMapper.convertValue(json,EngineAskRequest.class);
        MessageJob job = messagePublisher.publish(engineAskRequest);
        Object engins = engineAskEngineService.askEngine(engineAskRequest, job.getMethodContext());
        if(engins instanceof EngineNode){
            return Message.messageToResponse(Message.ok().data("engine", engins));
        }else if(engins instanceof EngineAskAsyncResponse){
            return Message.messageToResponse(Message.ok().data("engineResponse", engins));
        }
        return Message.messageToResponse(Message.ok());
    }

    @POST
    @Path("/createEngine")
    public Response createEngine(@Context HttpServletRequest req, Map<String,Object> json) throws IOException {
        EngineCreateRequest engineCreateRequest = objectMapper.convertValue(json, EngineCreateRequest.class);
        MessageJob job = messagePublisher.publish(engineCreateRequest);
        try {
            EngineNode engine = engineCreateService.createEngine(engineCreateRequest, job.getMethodContext());
            return Message.messageToResponse(Message.ok().data("engine", engine));
        } catch (DWCRetryException e) {
            Message message = Message.error(e.getMessage());
            message.setMethod("/api/linkisManager/createEngine");
            return  Message.messageToResponse(message);
        }
    }

    @POST
    @Path("/stopEngine")
    public Response stopEngine(@Context HttpServletRequest req, Map<String,Object> json) throws IOException {
        EngineStopRequest engineStopRequest = objectMapper.convertValue(json,EngineStopRequest.class);
        MessageJob job = messagePublisher.publish(engineStopRequest);
        engineStopService.stopEngine(engineStopRequest,job.getMethodContext());
        return Message.messageToResponse(Message.ok());
    }

    @POST
    @Path("/reuseEngine")
    public Response reuseEngine(@Context HttpServletRequest req, Map<String,Object> json) throws IOException {
        EngineReuseRequest engineReuseRequest = objectMapper.convertValue(json,EngineReuseRequest.class);
        try {
            EngineNode engine = engineReuseService.reuseEngine(engineReuseRequest);
            return Message.messageToResponse(Message.ok().data("engine", engine));
        } catch (DWCRetryException e) {
            Message message = Message.error(e.getMessage());
            message.setMethod("/api/linkisManager/reuseEngine");
            return  Message.messageToResponse(message);
        }
    }

    @GET
    @Path("/listAllNodeHealthyStatus")
    public Response listAllNodeHealthyStatus(@Context HttpServletRequest req, @QueryParam("onlyEditable") Boolean onlyEditable){
        NodeStatus[] nodeStatus = NodeStatus.values();
        return Message.messageToResponse(Message.ok().data("nodeStatus", nodeStatus));
    }
}
