import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from '../usuario-create/usuario.model';

@Component({
  selector: 'app-usuario-disable',
  templateUrl: './usuario-disable.component.html',
  styleUrls: ['./usuario-disable.component.css']
})
export class UsuarioDisableComponent implements OnInit {

  usuario!: Usuario 
  constructor( private usuarioService: UsuarioService,private router: Router, private route : ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    if(id)
    this.usuarioService.buscarPeloId(id).subscribe(usuario => {
      this.usuario = usuario
    });
  }

  desabilitar(): void {
    this.usuarioService.desabilitar(this.usuario).subscribe(()=>{
        this.usuarioService.exibirMensagem('Usuário desabilitado com sucesso! ')
        this.router.navigate(['/usuarios'])
    })
  }

  cancelar(): void {
    this.router.navigate(['/usuarios'])
  }

}
