package argendata.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticController {

	
	public StaticController(){
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView contactUs(){
		ModelAndView mav = new ModelAndView();
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView error(){
		ModelAndView mav = new ModelAndView();
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView api(){
		ModelAndView mav = new ModelAndView();
		
		return mav;
	}
	
}
