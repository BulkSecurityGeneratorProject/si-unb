import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaudeSharedModule } from 'app/shared';
import {
  MedicoComponent,
  MedicoDetailComponent,
  MedicoUpdateComponent,
  MedicoDeletePopupComponent,
  MedicoDeleteDialogComponent,
  medicoRoute,
  medicoPopupRoute
} from './';

const ENTITY_STATES = [...medicoRoute, ...medicoPopupRoute];

@NgModule({
  imports: [SaudeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MedicoComponent, MedicoDetailComponent, MedicoUpdateComponent, MedicoDeleteDialogComponent, MedicoDeletePopupComponent],
  entryComponents: [MedicoComponent, MedicoUpdateComponent, MedicoDeleteDialogComponent, MedicoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaudeMedicoModule {}
