package org.kdea.upload;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService{
	
	 @Autowired
	 private UploadDAO dao;
	 
/*	 public boolean overlapfname(String orginfname){
		 
		 System.out.println("���������̸�1"+orginfname);
		 UploadVO vo =dao.overlapfnameDAO(orginfname);
		 System.out.println("���������̸�2"+orginfname);
		 
		 if(vo.getOrginfname()!=null){
			 // ������ �� �̸��� ������ 
			 return true;
		 }
		 //return�� �ٿ��� �ٷ� �־�����
		return false;
	 }
	 //���� �̸� �˻��ؼ� �ߺ��Ǵ°� �ִ��� Ȯ�� �ؾ���
*/
	public boolean insertFileinfo(UploadVO vo) {
		// TODO Auto-generated method stub
		return dao.insertFileinfoDAO(vo);
	}
	
	public UploadVO getrealfname(String changename){
		
		return dao.getrealfilename(changename);
	}
	 
	 //���� �����̸� �� �����̸� �ּ� �־ �߰�
	 
	 //�ٿ�ε��Ҷ��� ������ �̸����� �˻��ؼ� ���� ���� �̸��� ������ �װɷ� �ѷ��ָ鼭 ���� ������ ������ ^^ ����
	 //�������̸��� �������� �־� �� ��ȣ�� �������� ���� �����̸����� �ٲ��̸� �Ѵ� �����;��Ұž�
	 
}
