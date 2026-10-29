import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const toastr = inject(ToastrService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error?.error?.errors?.messageEn) {
        toastr.error(error.error.errors.messageEn, 'Error');
      } else {
        toastr.error('Something went wrong', 'Error');
        console.error(error);
      }

      return throwError(() => error);
    })
  );
};