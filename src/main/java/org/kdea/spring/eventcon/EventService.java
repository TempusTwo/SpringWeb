package org.kdea.spring.eventcon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class EventService {
 
    @Autowired
    private EventDAO evtDao;
 
    public List<Event> getEvtList(){
        return evtDao.getEvtList();
    }
     
    public Event getEvt(int eno) {
        return evtDao.getEvt(eno);
    }
     
    public boolean insertEvt(Event evt) {
        return evtDao.insertEvt(evt);
    }

	public boolean editEvt(Event evt) {
		
		return evtDao.updateEvt(evt);
	}

	public boolean deleteEvt(int eno) {
		// TODO Auto-generated method stub
		return evtDao.deleteEvt(eno);
	}

	public List<Event> getSearchEvt(String word, String cate) {
		// TODO Auto-generated method stub
		if(cate.equals("����ְ���ü")){
			//�ְ���ü
			return evtDao.SearchEvt(word);
		}
		else if(cate.equals("������")){
			return evtDao.SearchEvtloc(word);
		}
		else if(cate.equals("��糯¥")){
			//��¥
			//world
			
			
			return evtDao.SearchEvtdate(word);
        }
		//������ ��ü����Ʈ
		return evtDao.getEvtList();
	}
     
}