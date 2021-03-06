package com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dto.CartDTO;
import com.dto.GoodsDTO;
import com.dto.OrderDTO;
import com.dto.PageDTO;

@Repository
public class GoodsDAO {
	@Autowired
	SqlSessionTemplate template;
	
	//상품목록리스트
	public List<GoodsDTO> goodsList(Map<String, String> map) {
		List<GoodsDTO> list = template.selectList("GoodsMapper.goodsList", map);
		return list;
	}
	
	//상품자세히보기
	public GoodsDTO goodsRetrieve(String gCode) {
		GoodsDTO dto = template.selectOne("GoodsMapper.goodsRetrieve", gCode);
		return dto;
	}

	public void cartAdd(CartDTO cDTO) {
		template.insert("CartMapper.cartAdd", cDTO);
		
	}

	public List<CartDTO> cartList(String userid) {
		List<CartDTO>list = template.selectList("CartMapper.cartList",userid);
		return list;
	}

	public void cartDelete(int num) {
		template.delete("CartMapper.cartDelete", num);
		
	}

	public void delAllCart(List<String> list) {
		template.delete("CartMapper.cartAllDel",list);
		
	}

	public void orderDone(OrderDTO oDTO) {
		int n = template.insert("CartMapper.orderDone", oDTO);
		
	}

	public CartDTO orderConfirmByNum(int num) {
		CartDTO cDTO = template.selectOne("CartMapper.cartByNum", num);
		return cDTO;
	}

	public List<CartDTO> orderAllConfirm(List<String> list) {
		List<CartDTO> cDTO = template.selectList("CartMapper.orderAllConfirm",list);
		return cDTO;
	}

	public void orderAllDone(List<OrderDTO> oDTO) {
		System.out.println("orderAllDoneDAO==="+oDTO);
		int n = template.insert("CartMapper.orderAllDone",oDTO);
		System.out.println("orderAllDoneDAO==="+n);
		
	}
	
	public int totalCount(HashMap<String, String>map) {
		int n =  template.selectOne("GoodsMapper.totalCount", map);
		return n;
	}

	public PageDTO goodsSearchList(HashMap<String, String> map, int curPage) {
		
		PageDTO pDTO = new PageDTO();
		int perPage = pDTO.getPerPage();
		int offset = (curPage - 1) * perPage;
		
		List<GoodsDTO> list = template.selectList("GoodsMapper.search", map, new RowBounds(offset, perPage));
		
		pDTO.setCurPage(curPage);
		pDTO.setList(list);
		pDTO.setTotalCount(totalCount(map));
		
		System.out.println("Dao에서======="+list.size());
		return pDTO;
	}

	public List<GoodsDTO> searchPC(HashMap<String, String> map) {
		List<GoodsDTO> list = template.selectList("GoodsMapper.searchPC",map);
		return list;
	}

	
	

}
