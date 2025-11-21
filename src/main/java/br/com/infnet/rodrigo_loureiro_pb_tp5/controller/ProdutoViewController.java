package br.com.infnet.rodrigo_loureiro_pb_tp5.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

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
