import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaudeSharedModule } from 'app/shared';
import {
  EstoqueComponent,
  EstoqueDetailComponent,
  EstoqueUpdateComponent,
  EstoqueDeletePopupComponent,
  EstoqueDeleteDialogComponent,
  estoqueRoute,
  estoquePopupRoute
} from './';

const ENTITY_STATES = [...estoqueRoute, ...estoquePopupRoute];

@NgModule({
  imports: [SaudeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstoqueComponent,
    EstoqueDetailComponent,
    EstoqueUpdateComponent,
    EstoqueDeleteDialogComponent,
    EstoqueDeletePopupComponent
  ],
  entryComponents: [EstoqueComponent, EstoqueUpdateComponent, EstoqueDeleteDialogComponent, EstoqueDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaudeEstoqueModule {}
