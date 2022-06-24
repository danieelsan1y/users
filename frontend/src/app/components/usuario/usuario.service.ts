import { Usuario } from './usuario-create/usuario.model';
import { Injectable } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  baseUrl = "http://localhost:8080/usuarios"
  urlRed = "http://localhost:8080/usuarios/status/Ativo"

  constructor(private snackBar: MatSnackBar, private http: HttpClient) { }

  exibirMensagem(msg: string): void {
    this.snackBar.open(msg, 'X', {
      duration: 3000,
      horizontalPosition: "right",
      verticalPosition: "top"
    })
  }
  criar(usuario: Usuario) : Observable<Usuario> {
    return this.http.post<Usuario>(this.baseUrl,usuario)
  }

  ler() : Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.urlRed)
  }

}
