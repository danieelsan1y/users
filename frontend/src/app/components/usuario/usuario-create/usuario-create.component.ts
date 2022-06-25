import { Usuario } from './usuario.model';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ThisReceiver } from '@angular/compiler';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-usuario-create',
  templateUrl: './usuario-create.component.html',
  styleUrls: ['./usuario-create.component.css']
})
export class UsuarioCreateComponent implements OnInit {
  
  pipe = new DatePipe('en-US');
  usuario: Usuario ={
    nome : '',
    email: '',
    telefone: '',
    cpf: '',
    rg: '',
    dataNascimento : '',
    situacao : ''
  }
  constructor(private usuarioService: UsuarioService,
    private router: Router) { }

  ngOnInit(): void {
  
  }

  criar(): void {
    this.usuario.situacao = 'Ativo'
    this.usuarioService.criar(this.usuario).subscribe(()=>{
      this.usuarioService.exibirMensagem('Usuário criado!')
      this.router.navigate(['/usuarios'])
    })
    
  }

  cancelar(): void {
    this.router.navigate(['/usuarios'])
  }

}
