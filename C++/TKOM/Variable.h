#ifndef VARIABLE_H
#define VARIABLE_H
#include <string>
using namespace std;
class Variable
{
    public:
        Variable();
        Variable(const Variable& v)
        {
            flag = v.getFlague();
        }

        Variable(int v)
        {
            intValue = v;
            flag = true;
        }

        Variable(string& v)
        {
            flag = false;
        }

        Variable(double v)
        {
            flag = true;
        }

        Variable(float v)
        {
            flag = true;
        }


        void setValue(const Variable& v)
        {
            flag = v.getFlague();
        }

        void setValue(int v)
        {
            flag = true;
        }

        void setValue(string v)
        {
            flag = false;
        }

        void setValue(double v)
        {
            flag = true;
        }

        void setValue(float v)
        {
            flag = true;
        }

        bool getFlague() const
        {
            return flag;
        }

        virtual ~Variable();
        ostream& operator<<(ostream& os)
        {
           // os<<intValue;
            return os;
        }
    protected:
    private:
        bool flag; //if flag == true, then object is numeric, otherwise is text
        int intValue;
        string stringValue;


};


#endif // VARIABLE_H
