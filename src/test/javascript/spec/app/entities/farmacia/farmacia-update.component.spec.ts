/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SaudeTestModule } from '../../../test.module';
import { FarmaciaUpdateComponent } from 'app/entities/farmacia/farmacia-update.component';
import { FarmaciaService } from 'app/entities/farmacia/farmacia.service';
import { Farmacia } from 'app/shared/model/farmacia.model';

describe('Component Tests', () => {
  describe('Farmacia Management Update Component', () => {
    let comp: FarmaciaUpdateComponent;
    let fixture: ComponentFixture<FarmaciaUpdateComponent>;
    let service: FarmaciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [FarmaciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FarmaciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FarmaciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FarmaciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Farmacia(123);
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
        const entity = new Farmacia();
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
