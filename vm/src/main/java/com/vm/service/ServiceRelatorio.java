package com.vm.service;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ServiceRelatorio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public byte[] gerarRelatorio (String nomeRelatorio, ServletContext servletContext) throws Exception{
		
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + nomeRelatorio + ".jasper";
		
		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, new HashMap(), connection);
		
		byte[] retorno = JasperExportManager.exportReportToPdf(print);
		
		connection.close();
		
		return retorno;
	}
	
	

}
