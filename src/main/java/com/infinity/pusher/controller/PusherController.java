package com.infinity.pusher.controller;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.pusher.config.AppProperties;
import com.infinity.pusher.config.JsonProperties;
import com.infinity.pusher.model.Pusher;
import com.infinity.pusher.model.Response;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@CrossOrigin
@RestController
@RequestMapping(value="/pusher/api/v1")
@PropertySource(value={"classpath:locker.json"})
public class PusherController {
	
	private static final Logger LOGGER = Logger.getLogger(PusherController.class);
	
	@Autowired
	AppProperties appProperties;
	
    @Autowired 
	JsonProperties jsonProperties;
	 
	
	@Value("${property.path}")
	private String path;
	
	@RequestMapping(value = "/push", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Response> pushCmd(@RequestBody Pusher pusher, HttpServletRequest request){
		LOGGER.info("PusherController pushCmd...");
		JSONParser parser = new JSONParser();
		Response response = new Response();
		Session session=null;
		Channel channel=null;
		try {
			Object obj = parser.parse(new FileReader(
					path+File.separator+"locker.json"));
 
            JSONObject jsonObject = (JSONObject) obj;
 
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			String node=pusher.getNode();
			session = jsch.getSession(((HashMap) jsonObject.get(node)).get("user").toString(),
					((HashMap) jsonObject.get(node)).get("host").toString(), Integer.parseInt(((HashMap) jsonObject.get(node)).get("port").toString()));
			session.setPassword(((HashMap) jsonObject.get(node)).get("password").toString());
			session.setConfig(config);
			session.connect();
			LOGGER.info("Connected.");
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(pusher.getCmd());
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			Thread.sleep(1000);
			byte[] tmp = new byte[1024];
			StringBuilder sb=new StringBuilder();
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				sb.append(new String(tmp, 0, i));
			}
			
			LOGGER.info("cmd output ::"+sb.toString());
			response.setCode("200");
			response.setMessage("success");
			response.setResponseObj(sb.toString());
			
		} catch (Exception e) {
			LOGGER.info("Exception::"+e.getMessage());
			
		}
		finally {
			channel.disconnect();
			session.disconnect();
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}

}
