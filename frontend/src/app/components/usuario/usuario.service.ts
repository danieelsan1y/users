import { Usuario } from './usuario-create/usuario.model';
import { Injectable } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar'
import { HttpClient } from '@angular/common/http';
import { catchError, EMPTY, map, Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  baseUrl = "http://localhost:8080/usuarios"
  urlRed = "http://localhost:8080/usuarios/situacao/Ativo"
  urlDisable ="http://localhost:8080/usuarios/situacao"

  constructor(private snackBar: MatSnackBar, private http: HttpClient) { }

  exibirMensagem(msg: string, isError : boolean =false): void {
    this.snackBar.open(msg, 'X', {
      duration: 3000,
      horizontalPosition: "right",
      verticalPosition: "top",
      panelClass: isError ? ['msg-error'] : ['msg-sucess']
    })
  }
  criar(usuario: Usuario) : Observable<Usuario> {
    return this.http.post<Usuario>(this.baseUrl,usuario).pipe(
    map((obj)=>obj), 
    catchError(e =>this.errorHandler(e))
    );
  }

  errorHandler(e: any) : Observable<any> {
    console.log(e.error.mensagem);
    this.exibirMensagem(e.error.mensagem, true)
    return EMPTY
  }

  ler() : Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.urlRed)
  }

  buscarPeloId(id: string):Observable<Usuario> {
    const url = `${this.baseUrl}/${id}`
    return this.http.get<Usuario>(url).pipe(
      map((obj)=>obj), 
      catchError(e =>this.errorHandler(e))
      );

  }
  atualizar(usuario: Usuario):Observable<Usuario> {
    const url = `${this.baseUrl}/${usuario.id}`
    return this.http.put<Usuario>(url,usuario).pipe(
      map((obj)=>obj), 
      catchError(e =>this.errorHandler(e))
      );
  }
  desabilitar(usuario: Usuario):Observable<Usuario> {
    const url = `${this.urlDisable}/${usuario.id}`
    usuario.situacao = 'Inativo'
    return this.http.put<Usuario>(url,usuario.situacao).pipe(
      map((obj)=>obj), 
      catchError(e =>this.errorHandler(e))
      );
  }
}
