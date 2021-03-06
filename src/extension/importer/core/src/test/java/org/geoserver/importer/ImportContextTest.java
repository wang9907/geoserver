/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * (c) 2001 - 2013 OpenPlans
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.importer;

import org.junit.Assert;
import org.junit.Test;

public class ImportContextTest {

    @Test
    public void testUpdateState() throws Exception {
        ImportContext context = new ImportContext(0);

        // verify this works correctly with no tasks
        context.updated();
        Assert.assertEquals(ImportContext.State.PENDING, context.getState());

        ImportTask t1 = new ImportTask(null);
        ImportTask t2 = new ImportTask(null);
        ImportTask t3 = new ImportTask(null);
        context.addTask(t1);
        context.addTask(t2);
        context.addTask(t3);

        Assert.assertEquals(ImportContext.State.PENDING, context.getState());

        t1.setState(ImportTask.State.READY);
        context.updated();
        Assert.assertEquals(ImportContext.State.PENDING, context.getState());

        t2.setState(ImportTask.State.READY);
        t3.setState(ImportTask.State.READY);
        context.updated();
        Assert.assertEquals(ImportContext.State.PENDING, context.getState());

        t1.setState(ImportTask.State.COMPLETE);
        t2.setState(ImportTask.State.COMPLETE);
        context.updated();
        Assert.assertEquals(ImportContext.State.PENDING, context.getState());

        t3.setState(ImportTask.State.COMPLETE);
        context.updated();
        Assert.assertEquals(ImportContext.State.COMPLETE, context.getState());
    }
}
