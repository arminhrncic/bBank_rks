//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace BloodBankHCI_API.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class Donacije
    {
        public int DonacijaId { get; set; }
        public Nullable<int> TransfuzijskiCentarId { get; set; }
        public Nullable<int> DonatorId { get; set; }
        public Nullable<int> KorisnikId { get; set; }
        public System.DateTime DatumDonacije { get; set; }
        public int BrojDoza { get; set; }
        public int Kolicina { get; set; }
        public bool UspjesnoRealizovana { get; set; }
        public bool Odbijena { get; set; }
        public string Napomena { get; set; }
    
        public virtual Donatori Donatori { get; set; }
        public virtual Korisnici Korisnici { get; set; }
        public virtual TransfuzijskiCentri TransfuzijskiCentri { get; set; }
    }
}
