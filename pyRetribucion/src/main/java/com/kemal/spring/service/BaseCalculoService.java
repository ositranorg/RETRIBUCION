package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.BaseCalculo;
import com.kemal.spring.domain.BaseCalculoRepository;

@Service
public class BaseCalculoService {
	@Autowired
	BaseCalculoRepository dao;

    public void save(BaseCalculo baseCalculo) {
    	dao.save(baseCalculo);
    }
	
    public void updateEliminarContenedorBi(int codaporte) {
    	dao.updateEliminarContenedorBi(codaporte);
    }    
    public List<BaseCalculo> findAll(){
		return dao.findBySEstado("1");
	}
    public Page<BaseCalculo> findByNcodigoApAndSEstado(Integer id,Pageable pageable){
    	
		 try {
				return dao.findByNcodigoApAndSEstado(id,"1",pageable);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
    public List<BaseCalculo> findByNcodigoAp(int codAporte){
    	
		 try {
			return dao.findByNcodigoApAndSEstado(codAporte, "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
    
}
