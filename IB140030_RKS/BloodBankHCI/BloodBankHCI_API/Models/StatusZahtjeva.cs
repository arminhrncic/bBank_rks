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
    
    public partial class StatusZahtjeva
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public StatusZahtjeva()
        {
            this.ZahtjevZaKrv = new HashSet<ZahtjevZaKrv>();
        }
    
        public int StatusZahtjevaId { get; set; }
        public string Naziv { get; set; }
    
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<ZahtjevZaKrv> ZahtjevZaKrv { get; set; }
    }
}