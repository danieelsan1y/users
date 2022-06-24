import { Component, OnInit } from '@angular/core';
import {Router} from'@angular/router'

@Component({
  selector: 'app-usuario-crud',
  templateUrl: './usuario-crud.component.html',
  styleUrls: ['./usuario-crud.component.css']
})
export class UsuarioCrudComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

 navigateUsuarioCreate(): void {
    this.router.navigate(['/usuarios/create'])
  }

}
