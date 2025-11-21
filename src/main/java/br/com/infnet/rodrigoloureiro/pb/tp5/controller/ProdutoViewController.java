package br.com.infnet.rodrigoloureiro.pb.tp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProdutoViewController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/produto")
  public String produto() {
    return "produto";
  }
}
