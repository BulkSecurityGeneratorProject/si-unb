/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaudeTestModule } from '../../../test.module';
import { DoencaDetailComponent } from 'app/entities/doenca/doenca-detail.component';
import { Doenca } from 'app/shared/model/doenca.model';

describe('Component Tests', () => {
  describe('Doenca Management Detail Component', () => {
    let comp: DoencaDetailComponent;
    let fixture: ComponentFixture<DoencaDetailComponent>;
    const route = ({ data: of({ doenca: new Doenca(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [DoencaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoencaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoencaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doenca).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
