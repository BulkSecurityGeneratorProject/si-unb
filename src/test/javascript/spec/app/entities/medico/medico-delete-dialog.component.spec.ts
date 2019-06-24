/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaudeTestModule } from '../../../test.module';
import { MedicoDeleteDialogComponent } from 'app/entities/medico/medico-delete-dialog.component';
import { MedicoService } from 'app/entities/medico/medico.service';

describe('Component Tests', () => {
  describe('Medico Management Delete Component', () => {
    let comp: MedicoDeleteDialogComponent;
    let fixture: ComponentFixture<MedicoDeleteDialogComponent>;
    let service: MedicoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [MedicoDeleteDialogComponent]
      })
        .overrideTemplate(MedicoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
