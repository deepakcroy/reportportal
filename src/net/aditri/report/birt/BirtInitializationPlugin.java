/*
 * $Id$
 *
 * Last changed on : $Date$
 * Last changed by : $Author$
 */
package net.aditri.report.birt;

import javax.servlet.ServletException;
import net.aditri.report.birt.BirtEngine;

public class BirtInitializationPlugin {
    public void init() throws ServletException {
        BirtEngine.initBirtConfig();
    }
    public void destroy() {
        BirtEngine.destroyBirtEngine();
    }

}
