package cn.org.craftsmen.ms.assist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.org.craftsmen.ms.assist.services.TranslationService;
import cn.org.craftsmen.ms.assist.web.request.TranslateRequest;
import cn.org.craftsmen.ms.assist.web.response.TranslateResponse;

@RestController
@RequestMapping("/trans")
public class TranslateController {
	
	@Autowired
	private TranslationService translationService;

	@RequestMapping(method=RequestMethod.POST)
	public TranslateResponse translate(@RequestBody TranslateRequest req) {
		
		TranslateResponse tr = new TranslateResponse();
		tr.setFrom(req.getFrom());
		tr.setTo(req.getTo());
		tr.setSource(req.getContent());
		tr.setTranslateResult(translationService.translate(req.getContent(), req.getFrom().getLocale(), req.getTo().getLocale()));
		
		return tr;
	}
}
