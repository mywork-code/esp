package com.apass.esp.nothing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/image")
public class ImageRedirectController {
    private static final Logger LOGGER  = LoggerFactory.getLogger(ImageRedirectController.class);

	/**
	 * 
	 * @return
	 */
	 @RequestMapping(value = "/redirectToIndex", method = RequestMethod.GET)
	 public ModelAndView redirectToIndex(){
		 LOGGER.info("");
	   return new ModelAndView("view/pageIndex");
	 }
	 
	 /**
	  * 
	  * @return
	  */
	 @RequestMapping(value = "/redirectToHandPick", method = RequestMethod.GET)
	 public ModelAndView redirectToHandPick(){
		 LOGGER.info("");
		 return new ModelAndView("view/pageHandpick");
	 }
}
