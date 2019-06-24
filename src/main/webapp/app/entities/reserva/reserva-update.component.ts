import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IReserva, Reserva } from 'app/shared/model/reserva.model';
import { ReservaService } from './reserva.service';

@Component({
  selector: 'jhi-reserva-update',
  templateUrl: './reserva-update.component.html'
})
export class ReservaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    cpfPaciente: [],
    nomePaciente: [],
    quantidade: [],
    horaReserva: []
  });

  constructor(protected reservaService: ReservaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reserva }) => {
      this.updateForm(reserva);
    });
  }

  updateForm(reserva: IReserva) {
    this.editForm.patchValue({
      id: reserva.id,
      cpfPaciente: reserva.cpfPaciente,
      nomePaciente: reserva.nomePaciente,
      quantidade: reserva.quantidade,
      horaReserva: reserva.horaReserva != null ? reserva.horaReserva.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reserva = this.createFromForm();
    if (reserva.id !== undefined) {
      this.subscribeToSaveResponse(this.reservaService.update(reserva));
    } else {
      this.subscribeToSaveResponse(this.reservaService.create(reserva));
    }
  }

  private createFromForm(): IReserva {
    return {
      ...new Reserva(),
      id: this.editForm.get(['id']).value,
      cpfPaciente: this.editForm.get(['cpfPaciente']).value,
      nomePaciente: this.editForm.get(['nomePaciente']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      horaReserva:
        this.editForm.get(['horaReserva']).value != null ? moment(this.editForm.get(['horaReserva']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReserva>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
