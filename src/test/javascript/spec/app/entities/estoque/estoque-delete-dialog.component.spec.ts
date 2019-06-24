/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaudeTestModule } from '../../../test.module';
import { EstoqueDeleteDialogComponent } from 'app/entities/estoque/estoque-delete-dialog.component';
import { EstoqueService } from 'app/entities/estoque/estoque.service';

describe('Component Tests', () => {
  describe('Estoque Management Delete Component', () => {
    let comp: EstoqueDeleteDialogComponent;
    let fixture: ComponentFixture<EstoqueDeleteDialogComponent>;
    let service: EstoqueService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [EstoqueDeleteDialogComponent]
      })
        .overrideTemplate(EstoqueDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstoqueDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstoqueService);
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
