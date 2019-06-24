/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SaudeTestModule } from '../../../test.module';
import { RemedioUpdateComponent } from 'app/entities/remedio/remedio-update.component';
import { RemedioService } from 'app/entities/remedio/remedio.service';
import { Remedio } from 'app/shared/model/remedio.model';

describe('Component Tests', () => {
  describe('Remedio Management Update Component', () => {
    let comp: RemedioUpdateComponent;
    let fixture: ComponentFixture<RemedioUpdateComponent>;
    let service: RemedioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [RemedioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RemedioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemedioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemedioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Remedio(123);
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
        const entity = new Remedio();
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
