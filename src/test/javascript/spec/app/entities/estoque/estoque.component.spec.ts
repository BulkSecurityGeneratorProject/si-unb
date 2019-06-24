/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaudeTestModule } from '../../../test.module';
import { EstoqueComponent } from 'app/entities/estoque/estoque.component';
import { EstoqueService } from 'app/entities/estoque/estoque.service';
import { Estoque } from 'app/shared/model/estoque.model';

describe('Component Tests', () => {
  describe('Estoque Management Component', () => {
    let comp: EstoqueComponent;
    let fixture: ComponentFixture<EstoqueComponent>;
    let service: EstoqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaudeTestModule],
        declarations: [EstoqueComponent],
        providers: []
      })
        .overrideTemplate(EstoqueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstoqueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstoqueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Estoque(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estoques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
