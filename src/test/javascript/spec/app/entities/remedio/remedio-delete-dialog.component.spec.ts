/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaudeTestModule } from '../../../test.module';
import { RemedioDeleteDialogComponent } from 'app/entities/remedio/remedio-delete-dialog.component';
import { RemedioService } from 'app/entities/remedio/remedio.service';

describe('Component Tests', () => {
  describe('Remedio Management Delete Component', () => {
    let comp: RemedioDeleteDialogComponent;
    let fixture: ComponentFixture<RemedioDeleteDialogComponent>;
    let service: RemedioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [RemedioDeleteDialogComponent]
      })
        .overrideTemplate(RemedioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemedioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemedioService);
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
