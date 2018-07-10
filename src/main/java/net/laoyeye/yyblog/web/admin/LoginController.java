package net.laoyeye.yyblog.web.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.laoyeye.yyblog.annotation.Log;
import net.laoyeye.yyblog.common.YYBlogResult;
import net.laoyeye.yyblog.enums.ResultEnum;
import net.laoyeye.yyblog.model.UserDO;
import net.laoyeye.yyblog.web.BaseController;


/**
 * created by laoyeye on 2018/1/14 at 12:47
 */
@Controller
public class LoginController extends BaseController{

	@GetMapping("/login")
	public String login() {
		UserDO user = getUser();
		if (user != null) {
			return "redirect:management/index";
		}
        return "management/login";
	}
	
	@Log("登陆验证")
	@PostMapping("/login")
	@ResponseBody
	public YYBlogResult login(String username, String password, Boolean remember) {
		if (remember == null) {
			remember = false;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			/*token.setRememberMe(remember);*/
			subject.login(token);
		} catch (Exception e) {
			return YYBlogResult.build(ResultEnum.UNKONW_ERROR.getCode(), e.getMessage());
		}
		return YYBlogResult.ok();
	}
}
