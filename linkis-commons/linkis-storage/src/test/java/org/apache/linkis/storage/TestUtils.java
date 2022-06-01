package org.apache.linkis.storage;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.statistics.Statistics;

import java.io.IOException;
import java.util.concurrent.Callable;

public class TestUtils {

    public static void enforceEmptyDir(Configuration conf, Path path) throws IOException {
        FileSystem fs = path.getFileSystem(conf);
        if (fs.exists(path)) {
            if (!fs.delete(path, true)) {
                throw new IOException("can not delete path " + path);
            }
        }
        if (!fs.mkdirs(path)) {
            throw new IOException("can not create path " + path);
        }
    }

//    /**
//     * A convenience method to avoid a large number of @Test(expected=...) tests
//     * @param message A String message to describe this assertion
//     * @param expected An Exception class that the Runnable should throw
//     * @param callable A Callable that is expected to throw the exception
//     */
//    public static void assertThrows(
//            String message, Class<? extends Exception> expected, Callable callable) {
//        try {
//            callable.call();
//            Assert.fail("No exception was thrown (" + message + "), expected: " +
//                    expected.getName());
//        } catch (Exception actual) {
//            try {
//                Assert.assertEquals(message, expected, actual.getClass());
//            } catch (AssertionError e) {
//                e.addSuppressed(actual);
//                throw e;
//            }
//        }
//    }
//
//    public static void assertStatsValuesEqual(Statistics<?> stats1, Statistics<?> stats2) {
//        assertStatsValuesEqual(null, stats1, stats2);
//    }
//
//    // To be used to assert that the values (min, max, num-of-nulls) equals. It might be used in cases when creating
//    // Statistics object for the proper Type would require too much work/code duplications etc.
//    public static void assertStatsValuesEqual(String message, Statistics<?> expected, Statistics<?> actual) {
//        if (expected == actual) {
//            return;
//        }
//        if (expected == null || actual == null) {
//            Assert.assertEquals(expected, actual);
//        }
//        Assert.assertThat(actual, CoreMatchers.instanceOf(expected.getClass()));
//        Assert.assertArrayEquals(message, expected.getMaxBytes(), actual.getMaxBytes());
//        Assert.assertArrayEquals(message, expected.getMinBytes(), actual.getMinBytes());
//        Assert.assertEquals(message, expected.getNumNulls(), actual.getNumNulls());
//    }
}
