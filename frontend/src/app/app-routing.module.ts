import { UsuarioUpdateComponent } from './components/usuario/usuario-update/usuario-update.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {HomeComponent} from './views/home/home.component';
import {UsuarioCrudComponent} from  './views/usuario-crud/usuario-crud.component'
import { UsuarioCreateComponent } from './components/usuario/usuario-create/usuario-create.component';

const routes: Routes = [
  {
  path: "",
  component : HomeComponent
},
{
  path:"usuarios",
  component: UsuarioCrudComponent
},
{
  path: "usuarios/create",
  component: UsuarioCreateComponent
},
{
  path: "usuarios/update/:id",
  component: UsuarioUpdateComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
