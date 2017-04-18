package com.apass.gfb.framework.cxf;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @description CXF Rest Status Query
 *
 * @author listening
 * @version $Id: StatusController.java, v 0.1 2015年8月16日 下午8:17:08 listening
 *          Exp$
 */
@Path("/listeningboot")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CxfRestHealthController {

	/**
	 * Query Status
	 * 
	 * @return String
	 */
	@GET
	@Path("/cxfrest/health")
	public String handleStatusQuery() {
		return "running";
	}
}
