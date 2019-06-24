import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaudeSharedModule } from 'app/shared';
import {
  FarmaciaComponent,
  FarmaciaDetailComponent,
  FarmaciaUpdateComponent,
  FarmaciaDeletePopupComponent,
  FarmaciaDeleteDialogComponent,
  farmaciaRoute,
  farmaciaPopupRoute
} from './';

const ENTITY_STATES = [...farmaciaRoute, ...farmaciaPopupRoute];

@NgModule({
  imports: [SaudeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FarmaciaComponent,
    FarmaciaDetailComponent,
    FarmaciaUpdateComponent,
    FarmaciaDeleteDialogComponent,
    FarmaciaDeletePopupComponent
  ],
  entryComponents: [FarmaciaComponent, FarmaciaUpdateComponent, FarmaciaDeleteDialogComponent, FarmaciaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaudeFarmaciaModule {}
