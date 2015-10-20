package org.kdea.spring.simpleboard;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; 
	
	
	private boolean leftMore, rightMore;
	
	int pg;
	int totalPg;
	int rowsPerPage = 5;
	int numsPerPage = 3;


	public boolean isLeftMore() {
		return leftMore;
	}

	public void setLeftMore(boolean leftMore) {
		this.leftMore = leftMore;
	}

	public boolean isRightMore() {
		return rightMore;
	}

	public void setRightMore(boolean rightMore) {
		this.rightMore = rightMore;
	}

/*	public List<BoardVO> svclist() {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		String sPage = request.getParameter("page");
		if (sPage != null) {
			pg = Integer.valueOf(sPage);
		} else {
			pg = 1;
		}
		List<BoardVO> list = dao.getPagelist(pg);
		//request.setAttribute("list", list);
		//NaviVO nav = getNaviVO();
		//request.setAttribute("nav", nav);
		return list;

	}*/
	public List<BoardVO> svclist(int pnum) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		return dao.getPagelist(pnum);
	}
	public NaviVO getNaviVO(int pnum) {
		// ������̼ǽ� �ʿ��� �������
		pg =pnum;
		NaviVO nav = new NaviVO();
		nav.setCurrPage(pg);// ���� ������
		nav.setLinks(getNavlinks(pg, rowsPerPage, numsPerPage));// ���� �������� ����
		nav.setLeftMore(leftMore);// ���� �̵� �ۼ�����
		nav.setRightMore(rightMore);// ������ �̵� �ۼ�����
		nav.setTatalPages(totalPg);
		System.out.println(nav.getLinks().length+"link");
		return nav;
	}
	/*public NaviVO getsearchNaviVO(int pnum,String cate, String word) {
		// ������̼ǽ� �ʿ��� �������
		pg =pnum;
		
		NaviVO nav = new NaviVO();
		nav.setCurrPage(pg);// ���� ������
		if(cate.equals("������")){
			nav.setLinks(getsearchNavlinks(pg, rowsPerPage, numsPerPage,word));
		}
		else if(cate.equals("�۾���")){
			nav.setLinks(getsearchNavlinks2(pg, rowsPerPage, numsPerPage,word));
		}
		//nav.setLinks(getsearchNavlinks(pg, rowsPerPage, numsPerPage));// ���� �������� ����
		nav.setLeftMore(leftMore);// ���� �̵� �ۼ�����
		nav.setRightMore(rightMore);// ������ �̵� �ۼ�����
		nav.setTatalPages(totalPg);
		System.out.println(nav.getLinks().length+"link");
		return nav;
	}*/
	/*public int[] getsearchNavlinks(int page, int rowsPerPage, int numsPerPage, String word) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int totalPages = (dao.getsearchTotalRows(word) - 1) / rowsPerPage + 1;
		System.out.println("totalrow "+dao.getTotalRows());
		System.out.println("totalPages "+totalPages);
		// �ο���� �ִ� ����(�� �ο�� ������ ����)�� 1�� ���� ���������� ����� ������ 1�� ���ϸ� �� ������ ���� ���´�(��
		// �������� ���� ���� ���ϴ� �İ� ����������)
		totalPg = totalPages;

		int tmp = (page - 1) / numsPerPage + 1;
		// �� �������� ���ϴ� ������ ���ܱ��� ����(��� ���� ��� �������� ���� ���� �����ϴ� �İ� ����)
		int end = tmp * numsPerPage;
		// ������ ���ܱ��� ������ ���ܱ��� �ִ� ����� ���ϸ� �� ������ ���ܱ��� �ִ� ���ڰ� ���´�.
		int start = (tmp - 1) * numsPerPage + 1;
		// �ش� ������ ���ܱ� �ٷ� �� ��ܱ��� �ִ� ���ڿ� 1�� ���ϸ� ���� ������ ���ܱ��� �ּ� ���ڰ� ���´�
		if (start == 1)
			leftMore = false;
		// Ȥ�� �� �ּ� ���ڰ� 1�� ��� ����(<<) ��ũ�� ������� �ʵ��� ��½� Ȯ���ϴ� boolean ���� false�� �ش�
		else
			leftMore = true;
		// �ƴ� ���(1���� Ŭ���) ���� ��ũ ��½� Ȯ���ϴ� boolean ���� true�� �ش�
		if (end >= totalPages) {
			end = totalPages;
			rightMore = false;
			// Ȥ�� �ִ� ���ڰ� �� ������ ���ں��� ũ�ų� ���� ��� ������(>>) ��ũ�� ������� �ʵ��� ��½� Ȯ���ϴ�
			// boolean ���� false�� �ش�
		} else
			rightMore = true;
		// �ƴҰ��(�� ������ ������ �ִ������ �� ������ ���ڰ� Ŭ���) ������ ��ũ ��½� Ȯ���ϴ� boolean ���� true��
		// �ش�
		int[] links = new int[end - start + 1];
		// ������ �ѹ����� ��� �迭�� �����. �� ũ��� �ִ� ����-�ּҼ���+1�� �ϸ� �� ������ �ִ� ���ڿ� �ּ� ������ ���̰�
		// �����ϴٰ� �Ҽ� ���� ����

		// ��) 5������ �� ����ϴ� ������ ��ũ ������ ���������� �� 13 �������� ��� 1,2��° ��ũ���� �ּ� �ִ� ���� 4������
		// 3��°
		// ��ũ ���� �ִ�, �ּ����̴� 2 ���ȴ�
		for (int num = start, i = 0; num <= end; num++, i++) {
			links[i] = num;
		}
		// ������ ���� �迭�� ���� �ѹ����� ������ �ѹ����� ���� �ִ´�.
		System.out.println(links.length+"link");
		return links;
	}
	public int[] getsearchNavlinks2(int page, int rowsPerPage, int numsPerPage, String word) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int totalPages = (dao.getsearchTotalRows2(word) - 1) / rowsPerPage + 1;
		System.out.println("totalrow "+dao.getTotalRows());
		System.out.println("totalPages "+totalPages);
		// �ο���� �ִ� ����(�� �ο�� ������ ����)�� 1�� ���� ���������� ����� ������ 1�� ���ϸ� �� ������ ���� ���´�(��
		// �������� ���� ���� ���ϴ� �İ� ����������)
		totalPg = totalPages;

		int tmp = (page - 1) / numsPerPage + 1;
		// �� �������� ���ϴ� ������ ���ܱ��� ����(��� ���� ��� �������� ���� ���� �����ϴ� �İ� ����)
		int end = tmp * numsPerPage;
		// ������ ���ܱ��� ������ ���ܱ��� �ִ� ����� ���ϸ� �� ������ ���ܱ��� �ִ� ���ڰ� ���´�.
		int start = (tmp - 1) * numsPerPage + 1;
		// �ش� ������ ���ܱ� �ٷ� �� ��ܱ��� �ִ� ���ڿ� 1�� ���ϸ� ���� ������ ���ܱ��� �ּ� ���ڰ� ���´�
		if (start == 1)
			leftMore = false;
		// Ȥ�� �� �ּ� ���ڰ� 1�� ��� ����(<<) ��ũ�� ������� �ʵ��� ��½� Ȯ���ϴ� boolean ���� false�� �ش�
		else
			leftMore = true;
		// �ƴ� ���(1���� Ŭ���) ���� ��ũ ��½� Ȯ���ϴ� boolean ���� true�� �ش�
		if (end >= totalPages) {
			end = totalPages;
			rightMore = false;
			// Ȥ�� �ִ� ���ڰ� �� ������ ���ں��� ũ�ų� ���� ��� ������(>>) ��ũ�� ������� �ʵ��� ��½� Ȯ���ϴ�
			// boolean ���� false�� �ش�
		} else
			rightMore = true;
		// �ƴҰ��(�� ������ ������ �ִ������ �� ������ ���ڰ� Ŭ���) ������ ��ũ ��½� Ȯ���ϴ� boolean ���� true��
		// �ش�
		int[] links = new int[end - start + 1];
		// ������ �ѹ����� ��� �迭�� �����. �� ũ��� �ִ� ����-�ּҼ���+1�� �ϸ� �� ������ �ִ� ���ڿ� �ּ� ������ ���̰�
		// �����ϴٰ� �Ҽ� ���� ����

		// ��) 5������ �� ����ϴ� ������ ��ũ ������ ���������� �� 13 �������� ��� 1,2��° ��ũ���� �ּ� �ִ� ���� 4������
		// 3��°
		// ��ũ ���� �ִ�, �ּ����̴� 2 ���ȴ�
		for (int num = start, i = 0; num <= end; num++, i++) {
			links[i] = num;
		}
		// ������ ���� �迭�� ���� �ѹ����� ������ �ѹ����� ���� �ִ´�.
		System.out.println(links.length+"link");
		return links;
	}*/
	public int[] getNavlinks(int page, int rowsPerPage, int numsPerPage) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int totalPages = (dao.getTotalRows() - 1) / rowsPerPage + 1;
		System.out.println("totalrow "+dao.getTotalRows());
		System.out.println("totalPages "+totalPages);
		// �ο���� �ִ� ����(�� �ο�� ������ ����)�� 1�� ���� ���������� ����� ������ 1�� ���ϸ� �� ������ ���� ���´�(��
		// �������� ���� ���� ���ϴ� �İ� ����������)
		totalPg = totalPages;

		int tmp = (page - 1) / numsPerPage + 1;
		// �� �������� ���ϴ� ������ ���ܱ��� ����(��� ���� ��� �������� ���� ���� �����ϴ� �İ� ����)
		int end = tmp * numsPerPage;
		// ������ ���ܱ��� ������ ���ܱ��� �ִ� ����� ���ϸ� �� ������ ���ܱ��� �ִ� ���ڰ� ���´�.
		int start = (tmp - 1) * numsPerPage + 1;
		// �ش� ������ ���ܱ� �ٷ� �� ��ܱ��� �ִ� ���ڿ� 1�� ���ϸ� ���� ������ ���ܱ��� �ּ� ���ڰ� ���´�
		if (start == 1)
			leftMore = false;
		// Ȥ�� �� �ּ� ���ڰ� 1�� ��� ����(<<) ��ũ�� ������� �ʵ��� ��½� Ȯ���ϴ� boolean ���� false�� �ش�
		else
			leftMore = true;
		// �ƴ� ���(1���� Ŭ���) ���� ��ũ ��½� Ȯ���ϴ� boolean ���� true�� �ش�
		if (end >= totalPages) {
			end = totalPages;
			rightMore = false;
			// Ȥ�� �ִ� ���ڰ� �� ������ ���ں��� ũ�ų� ���� ��� ������(>>) ��ũ�� ������� �ʵ��� ��½� Ȯ���ϴ�
			// boolean ���� false�� �ش�
		} else
			rightMore = true;
		// �ƴҰ��(�� ������ ������ �ִ������ �� ������ ���ڰ� Ŭ���) ������ ��ũ ��½� Ȯ���ϴ� boolean ���� true��
		// �ش�
		int[] links = new int[end - start + 1];
		// ������ �ѹ����� ��� �迭�� �����. �� ũ��� �ִ� ����-�ּҼ���+1�� �ϸ� �� ������ �ִ� ���ڿ� �ּ� ������ ���̰�
		// �����ϴٰ� �Ҽ� ���� ����

		// ��) 5������ �� ����ϴ� ������ ��ũ ������ ���������� �� 13 �������� ��� 1,2��° ��ũ���� �ּ� �ִ� ���� 4������
		// 3��°
		// ��ũ ���� �ִ�, �ּ����̴� 2 ���ȴ�
		for (int num = start, i = 0; num <= end; num++, i++) {
			links[i] = num;
		}
		// ������ ���� �迭�� ���� �ѹ����� ������ �ѹ����� ���� �ִ´�.
		System.out.println(links.length+"link");
		return links;
	}

	public BoardVO svcinfo(int bnum) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
    	BoardVO vo = dao.info(bnum);
		return vo ;
	}

	public boolean svcinsert(BoardVO board) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int rows = dao.insert(board);
		boolean check = rows>0 ? true : false;
		return check;
	}

	public boolean svcupdate(BoardVO vo) {
		
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int rows = dao.update(vo);
		
		boolean check = rows>0 ? true : false; 
		return check;
	}

	public boolean svcdelete(int i) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int rows = dao.delete(i);
		boolean check = rows>0 ? true : false;
		return check;
	}

	public List<BoardVO> svcsearch(String word, String cate) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		List<BoardVO> list = new ArrayList<BoardVO>(); //svclist(pg);
		
		if(cate.equals("������")){
			list = dao.search(word);
		}
		else if(cate.equals("�۾���")){
			list = dao.searcha(word);
		}
		//������ ��ü����Ʈ
		
		
		return list;
	}

	

}
