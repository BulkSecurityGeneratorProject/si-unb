import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SaudeSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SaudeSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SaudeSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaudeSharedModule {
  static forRoot() {
    return {
      ngModule: SaudeSharedModule
    };
  }
}
