package br.com.alura.listavip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.alura.enviadorEmail.EmailService;
import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.service.ConvidadoService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ConvidadoController {

    @Autowired
    private ConvidadoService service;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("listaconvidados")
    public String listaConvidados(Model model){

        Iterable<Convidado> convidados = service.obterTodos();

        model.addAttribute("convidados", convidados);

        return "listaconvidados";
    }

    @RequestMapping(value = "salvar", method = RequestMethod.POST )
    public String salvar(@RequestParam("nome") String nome, @RequestParam("email")String email,
                         @RequestParam("telefone") String telefone, Model model){

        Convidado novoConvidado = new Convidado(nome, email, telefone);

        service.salvar(novoConvidado);

        new EmailService().enviar(nome, email);

        Iterable<Convidado> convidados = service.obterTodos();
        model.addAttribute("convidados", convidados);
        return "listaconvidados";
    }

    @RequestMapping("/listaconvidados/{id}")
        public ModelAndView findForId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/detalhe");
        Convidado convidado = service.obterPorId(id);
        modelAndView.addObject("convidado", convidado);
        return modelAndView;
    }

    @RequestMapping("/{nome}")
    public ModelAndView findForName(@PathVariable("nome") String nome) {
        ModelAndView modelAndView = new ModelAndView("/detalhe");
        List<Convidado> convidado = service.obterPorNome(nome);
        modelAndView.addObject("convidado", convidado);
        return modelAndView;
    }
}