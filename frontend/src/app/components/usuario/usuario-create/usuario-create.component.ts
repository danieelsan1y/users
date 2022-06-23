import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuario-create',
  templateUrl: './usuario-create.component.html',
  styleUrls: ['./usuario-create.component.css']
})
export class UsuarioCreateComponent implements OnInit {

  constructor(private usuarioService: UsuarioService,
    private router: Router) { }

  ngOnInit(): void {
  
  }

  criar(): void {
    this.usuarioService.exibirMensagem('Usuário criado!')
  }

  cancelar(): void {
    this.router.navigate(['/usuarios'])
  }

}
