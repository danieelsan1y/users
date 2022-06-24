import { UsuarioService } from './../usuario.service';
import { Usuario } from './../usuario-create/usuario.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-usuario-read',
  templateUrl: './usuario-read.component.html',
  styleUrls: ['./usuario-read.component.css']
})
export class UsuarioReadComponent implements OnInit {
  
  usuarios: Usuario[] =[];
  displayedColumns = ['id','nome','email','telefone','cpf','rg','dataNascimento','idade']

  constructor(private usuarioService : UsuarioService) { }

  ngOnInit(): void {
    this.usuarioService.ler().subscribe((usuarios: Usuario[])=> {
      this.usuarios = usuarios;
      console.log(usuarios)
    })

  }

}
