package org.kdea.spring.websocket;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SimpleWebSocketHandler extends TextWebSocketHandler {
	private static Map<String, WebSocketSession> sessionMap = new HashMap<>();
	private static List<String> list = new ArrayList<>();
	
	// ������ �������� �ؽ�Ʈ �޽����� �����Ǹ� ȣ��Ǵ� �޼ҵ�
	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		String payloadMessage = (String) message.getPayload();
		System.out.println("������ ������ �޽���:" + payloadMessage);

		/*
		 * Iterator<String> keys = sessionMap.keySet().iterator(); while
		 * (keys.hasNext()) { String key = keys.next();
		 * sessionMap.get(key).sendMessage(new TextMessage(payloadMessage)); }
		 */

		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObj = (JSONObject) jsonParser.parse(payloadMessage);

			String receiver = (String) jsonObj.get("receiver");

			try {
				if (receiver.equals("default")) {
					Iterator<String> keys = sessionMap.keySet().iterator();
					while (keys.hasNext()) {
						String key = keys.next();
	
						sessionMap.get(key).sendMessage(new TextMessage(payloadMessage));
					}
				} else {
					Map<String, Object> map = session.getAttributes();
					String usrId = (String) map.get("usrId");
					sessionMap.get(usrId).sendMessage(
							new TextMessage(payloadMessage));
					sessionMap.get(receiver).sendMessage(
							new TextMessage(payloadMessage));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Map<String, Object> map = session.getAttributes();
		String usrId = (String) map.get("usrId");
		
		System.out.println("������ ���̵�:" + usrId);
	}

	// ������ ������ Ŭ���̾�Ʈ�� �����ϸ� ȣ��Ǵ� �޼ҵ�
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {

		super.afterConnectionEstablished(session);
		System.out.println("Ŭ���̾�Ʈ ���ӵ�");

		Map<String, Object> map = session.getAttributes();
		String id = (String) map.get("usrId");

		sessionMap.put(id, session);
		
		Iterator<String> keys = sessionMap.keySet().iterator(); 
		
		list.add(id);	
		
		while(keys.hasNext()) { 
			String key = keys.next();
			sessionMap.get(key).sendMessage(new TextMessage("{'order':'usrappend','list':"+list+"}"));
			
			System.out.println("Ŭ���̾�Ʈ ������ �߰� "+"{'order':'usrappend','usrId':"+id+"}"+" ����");
		 }
		
		 
	}

	// Ŭ���̾�Ʈ�� ������ �����ϸ� ȣ��Ǵ� �޼ҵ�
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);

		Map<String, Object> map = session.getAttributes();
		String id = (String) map.get("usrId");

		sessionMap.remove(id);
		list.remove(id);
		
		
		Iterator<String> keys = sessionMap.keySet().iterator();

	
		while (keys.hasNext()) { String key = keys.next();
		 
		sessionMap.get(key).sendMessage(new TextMessage("{'order':'usrappend','list':"+list+"}"));
		}
		

		System.out.println("Ŭ���̾�Ʈ ��������");
	}

	// �޽��� ���۽ó� ���������� ������ �߻��� �� ȣ��Ǵ� �޼ҵ�
	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
		System.out.println("���ۿ��� �߻�");
	}
}