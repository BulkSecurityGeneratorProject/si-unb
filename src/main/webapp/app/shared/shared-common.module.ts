import { NgModule } from '@angular/core';

import { SaudeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [SaudeSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [SaudeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SaudeSharedCommonModule {}
