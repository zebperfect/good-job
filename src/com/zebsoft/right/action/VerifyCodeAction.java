package com.zebsoft.right.action;

import java.io.ByteArrayInputStream;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.zzz.util.RandomCodeUtil;

@Controller("verifyCodeAction")
@Scope("prototype")
public class VerifyCodeAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private ByteArrayInputStream inputStream;

	public String execute() throws Exception {
		RandomCodeUtil rdnu = RandomCodeUtil.Instance();
		this.setInputStream(rdnu.getImage());// 取得带有随机字符串的图片
		ActionContext.getContext().getSession().put("random", rdnu.getString());// 取得随机字符串放入HttpSession
		return SUCCESS;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
}