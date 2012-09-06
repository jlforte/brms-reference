Given there are these applications
| appId | amount | custId |
| 100   | 8000   | 1      |

Given these customers
|  id  |   name   | age | creditScore |
|1     |Bob       |20   | 700         |

When I evaluate these objects in the mortgage application

Then I except the mortgages added to be
| custId | appId | amount |
|1       | 100   | 8000  |