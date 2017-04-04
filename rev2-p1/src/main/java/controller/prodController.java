package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ValueOutputStream;

import helper.JsonHelper;
import model.Produto;
import model.Resultado;


@WebServlet(urlPatterns = "/controller")

public class prodController extends HttpServlet {

	private List<Object> lista = new ArrayList<Object>();
	private JsonHelper jsonHelper = new JsonHelper();
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String descricao = req.getParameter("descricao");
		double valor = Double.parseDouble(req.getParameter("valor"));

		Produto prod = new Produto(lista.size()+1,descricao, valor);
		lista.add(prod);
		
		resp.getWriter().println("{ id:" + prod.getId() + "  , descricao: " + prod.getDescricao() + ", valor: "
				+ prod.getValor() + " } ");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = req.getParameter("op");
		String json;
		if(op.equals("soma")){
			double soma =0;
			for(int i=0;i<lista.size();i++){
				soma +=((Produto) lista.get(i)).getValor();
			}
			resp.getWriter().println("{ soma:" + soma + " } ");
		}else{
		try {
			json = jsonHelper.gerarJsonLista(lista);
			resp.getWriter().print(json);
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String descricao = req.getParameter("descricao");
		double valor = Double.parseDouble(req.getParameter("valor"));
		int id = Integer.parseInt(req.getParameter("id"));
		for(int i=0;i<lista.size();i++){
			if(id == ((Produto) lista.get(i)).getId()){
				((Produto) lista.get(i)).setDescricao(descricao);
				((Produto) lista.get(i)).setValor(valor);
			}
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		for(int i=0;i<lista.size();i++){
			if(id == ((Produto) lista.get(i)).getId()){
				lista.remove(i);
			}
		}
	}

}
