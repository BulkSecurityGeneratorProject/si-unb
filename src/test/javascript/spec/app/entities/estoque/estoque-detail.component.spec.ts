/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaudeTestModule } from '../../../test.module';
import { EstoqueDetailComponent } from 'app/entities/estoque/estoque-detail.component';
import { Estoque } from 'app/shared/model/estoque.model';

describe('Component Tests', () => {
  describe('Estoque Management Detail Component', () => {
    let comp: EstoqueDetailComponent;
    let fixture: ComponentFixture<EstoqueDetailComponent>;
    const route = ({ data: of({ estoque: new Estoque(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [EstoqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstoqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstoqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estoque).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
