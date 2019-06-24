import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaudeSharedModule } from 'app/shared';
import {
  RemedioComponent,
  RemedioDetailComponent,
  RemedioUpdateComponent,
  RemedioDeletePopupComponent,
  RemedioDeleteDialogComponent,
  remedioRoute,
  remedioPopupRoute
} from './';

const ENTITY_STATES = [...remedioRoute, ...remedioPopupRoute];

@NgModule({
  imports: [SaudeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RemedioComponent,
    RemedioDetailComponent,
    RemedioUpdateComponent,
    RemedioDeleteDialogComponent,
    RemedioDeletePopupComponent
  ],
  entryComponents: [RemedioComponent, RemedioUpdateComponent, RemedioDeleteDialogComponent, RemedioDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaudeRemedioModule {}
