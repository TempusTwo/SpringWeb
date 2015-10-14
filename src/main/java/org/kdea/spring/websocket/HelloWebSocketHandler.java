package org.kdea.spring.websocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.websocket.Session;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
 
public class HelloWebSocketHandler extends TextWebSocketHandler {
	private static Map<String, WebSocketSession> sessionMap = new HashMap<>();
	private static int num=0;
	
    // ������ �������� �ؽ�Ʈ �޽����� �����Ǹ� ȣ��Ǵ� �޼ҵ�
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payloadMessage = (String) message.getPayload();
        System.out.println("������ ������ �޽���:"+payloadMessage);
        
        Iterator<String> keys = sessionMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			/*sessionMap.get(key).sendMessage(new TextMessage("ECHO : " + payloadMessage));*/
			sessionMap.get(key).sendMessage(new TextMessage(payloadMessage));
		}
        
    }
 
    // ������ ������ Ŭ���̾�Ʈ�� �����ϸ� ȣ��Ǵ� �޼ҵ�
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
         
        super.afterConnectionEstablished(session);
        sessionMap.put(Integer.toString(num++), session);
        
        System.out.println("Ŭ���̾�Ʈ ���ӵ�");
    }
 
    // Ŭ���̾�Ʈ�� ������ �����ϸ� ȣ��Ǵ� �޼ҵ�
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
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