import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'medico',
        loadChildren: './medico/medico.module#SaudeMedicoModule'
      },
      {
        path: 'farmacia',
        loadChildren: './farmacia/farmacia.module#SaudeFarmaciaModule'
      },
      {
        path: 'remedio',
        loadChildren: './remedio/remedio.module#SaudeRemedioModule'
      },
      {
        path: 'doenca',
        loadChildren: './doenca/doenca.module#SaudeDoencaModule'
      },
      {
        path: 'estoque',
        loadChildren: './estoque/estoque.module#SaudeEstoqueModule'
      },
      {
        path: 'reserva',
        loadChildren: './reserva/reserva.module#SaudeReservaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaudeEntityModule {}
