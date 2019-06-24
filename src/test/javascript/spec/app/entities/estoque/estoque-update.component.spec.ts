/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SaudeTestModule } from '../../../test.module';
import { EstoqueUpdateComponent } from 'app/entities/estoque/estoque-update.component';
import { EstoqueService } from 'app/entities/estoque/estoque.service';
import { Estoque } from 'app/shared/model/estoque.model';

describe('Component Tests', () => {
  describe('Estoque Management Update Component', () => {
    let comp: EstoqueUpdateComponent;
    let fixture: ComponentFixture<EstoqueUpdateComponent>;
    let service: EstoqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [EstoqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstoqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstoqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstoqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Estoque(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Estoque();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
