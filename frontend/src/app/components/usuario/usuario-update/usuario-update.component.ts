import { Usuario } from './../usuario-create/usuario.model';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-usuario-update',
  templateUrl: './usuario-update.component.html',
  styleUrls: ['./usuario-update.component.css']
})
export class UsuarioUpdateComponent implements OnInit {
  
  usuario!: Usuario 

  constructor(private usuarioService: UsuarioService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    if(id)
    this.usuarioService.buscarPeloId(id).subscribe(usuario => {
      this.usuario = usuario
    });
   }


  atualizar(): void {
    this.usuarioService.atualizar(this.usuario).subscribe(()=>{
      if(this.usuario.situacao == 'Inativo') {
        this.usuarioService.exibirMensagem('Produto desativado com sucesso! ')
      } else {
        this.usuarioService.exibirMensagem('Produto atualizado com sucesso! ')
        this.router.navigate(['/usuarios'])
      }

    })
  }

  cancelar(): void {
    this.router.navigate(['/usuarios'])
  }
}
