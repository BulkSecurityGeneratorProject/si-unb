/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaudeTestModule } from '../../../test.module';
import { RemedioDetailComponent } from 'app/entities/remedio/remedio-detail.component';
import { Remedio } from 'app/shared/model/remedio.model';

describe('Component Tests', () => {
  describe('Remedio Management Detail Component', () => {
    let comp: RemedioDetailComponent;
    let fixture: ComponentFixture<RemedioDetailComponent>;
    const route = ({ data: of({ remedio: new Remedio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [RemedioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RemedioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemedioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.remedio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
