package cn.org.craftsmen.ms.assists.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String home() {
		return "Assist services online";
	}
}
